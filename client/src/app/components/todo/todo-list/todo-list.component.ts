import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { TodoService } from 'src/app/services/todo.service';
import { UserService } from 'src/app/services/user.service';
import { ToDo } from 'src/app/shared/todo';

@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.css']
})
export class TodoListComponent implements OnInit {
  todos: ToDo[] = []; 
  filteredTodos: ToDo[] = []; 
  filter: boolean | null = null;  // null = tous, false = To Do, true = Done

  todoForm : FormGroup;
  currentUserId : number | null | undefined;

  constructor(private todoService: TodoService, private toaster : ToastrService, private route : Router, private userService : UserService) {
    this.todoForm = new FormGroup({
      title : new FormControl('', Validators.required),
      description : new FormControl('', Validators.required),
    }) 
   }

  ngOnInit() {
    this.currentUserId = this.userService.getCurrentUserId();
    console.log('User ID:', this.currentUserId);
    this.loadToDos();
  }

  loadToDos() {
    if(this.currentUserId) {
      this.todoService.getTodosByUser(this.currentUserId).subscribe(
        {
          next: (todos: ToDo[]) => {
              this.todos = todos;
              this.filteredTodos = todos; 
          },
          error: () => {
              this.toaster.error('Erreur lors du chargement des tâches', 'Erreur');
          }
        }
      );
    }
  }

  filterTodos(status: boolean | null) {
    this.filter = status;
    if (status === null) {
      this.filteredTodos = this.todos;
    } else {
      this.filteredTodos = this.todos.filter(todo => todo.status === status);
    }
  }

  addToDo() {
    if (this.todoForm.valid && this.currentUserId) {
      const todoData = {
        ...this.todoForm.value,
        status: false 
      };

      console.log('Data to send:', todoData);

      this.todoService.addToDo(this.currentUserId, todoData).subscribe(
       {
        next: (response) => {
          console.log('Response:', response);
          if (response.status === 201) {
            this.toaster.success('ToDo ajouté avec succès', 'Succès');
            window.location.reload();
            this.loadToDos(); 
            this.todoForm.reset(); 
          } else {
            this.toaster.error('Erreur lors de l\'ajout du ToDo', 'Erreur');
          }
        },
        error: (err) => {
          console.error('Error:', err);
          this.toaster.error('Erreur lors de l\'ajout du ToDo', 'Erreur');
        }
       }
      );
    } else {
      this.toaster.error('Veuillez remplir tous les champs', 'Formulaire invalide');
    }
  }
}
