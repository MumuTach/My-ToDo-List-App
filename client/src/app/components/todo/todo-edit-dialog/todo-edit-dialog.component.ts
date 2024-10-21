import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { TodoService } from 'src/app/services/todo.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToDo } from 'src/app/shared/todo';

@Component({
  selector: 'app-todo-edit-dialog',
  templateUrl: './todo-edit-dialog.component.html',
  styleUrls: ['./todo-edit-dialog.component.css']
})
export class TodoEditDialogComponent {
  todoEditForm: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<TodoEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ToDo,
    private todoService: TodoService,
    private toaster: ToastrService
  ) {
    this.todoEditForm = new FormGroup({
      title: new FormControl(this.data.title, Validators.required),
      description: new FormControl(this.data.description, Validators.required),
      status: new FormControl(this.data.status) 
    });
  }

  updateTodo() {
    if (this.todoEditForm.valid) {
      const updatedTodo = { ...this.data, ...this.todoEditForm.value };
      this.todoService.updateTodo(this.data.id, updatedTodo).subscribe({
        next: () => {
          this.toaster.success('Tâche mise à jour avec succès', 'Succès');
          this.dialogRef.close(true); 
          window.location.reload(); 
        },
        error: () => {
          this.toaster.error('Erreur lors de la mise à jour', 'Erreur');
        }
      });
    } else {
      this.toaster.error('Veuillez remplir tous les champs', 'Formulaire invalide');
    }
  }

  closeDialog() {
    this.dialogRef.close();
  }
}
