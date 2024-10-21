import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { TodoService } from 'src/app/services/todo.service';
import { ToDo } from 'src/app/shared/todo';
import { TodoEditDialogComponent } from '../todo-edit-dialog/todo-edit-dialog.component';

@Component({
  selector: 'app-todo-item',
  templateUrl: './todo-item.component.html',
  styleUrls: ['./todo-item.component.css']
})
export class TodoItemComponent {  
  @Input() todo!: ToDo;

  constructor(private todoService: TodoService, private toaster : ToastrService, private dialog : MatDialog){}

  delete() {
    this.todoService.deleteToDo(this.todo.id).subscribe({
      next: () => {
        this.toaster.success('Tâche supprimée avec succès', 'Succès');
        window.location.reload(); 
      },
      error: () => {
        this.toaster.error('Erreur lors de la suppression', 'Erreur');
      }
    });
  }

  edit() {
    const dialogRef = this.dialog.open(TodoEditDialogComponent, {
      data: this.todo,
      width: '65%',
      maxWidth: '400px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log('Tâche mise à jour avec succès !');
      }
    });
  }

  markAsDone() {
    const updatedTodo = { ...this.todo, status: true }; 
    this.todoService.updateTodo(this.todo.id, updatedTodo).subscribe({
      next: () => {
        this.toaster.success('Tâche marquée comme terminée', 'Succès');
        window.location.reload(); 
        this.todo.status = true;
      },
      error: () => {
        this.toaster.error('Erreur lors de la mise à jour', 'Erreur');
      }
    });
  }
}
