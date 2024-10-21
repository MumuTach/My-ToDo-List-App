import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToDo } from '../shared/todo';

@Injectable({
  providedIn: 'root'
})
export class TodoService {

  url = 'http://localhost:8000';

  constructor(private http : HttpClient) { }

  getTodosByUser(id : Number) {
    return this.http.get<ToDo[]>(this.url + '/todo/' + id, {
      headers : new HttpHeaders().set('Content-type', 'application/json')
    })
  }

  getTodosByUserAndStatus(userId : number, status : boolean) {
    return this.http.get<ToDo[]>(this.url + '/todo/' + userId + '/' + status, {
      headers : new HttpHeaders().set('Content-type', 'application/json')
    })
  }

  addToDo(userId : number, todoForm : any) {
    return this.http.post(this.url + '/todo/add/' + userId, todoForm, {
      headers : new HttpHeaders().set('Content-type', 'application/json'),
      observe: 'response'
    });
  }

  deleteToDo(id : number) {
    return this.http.delete(this.url + '/todo/' + id, {
      headers : new HttpHeaders().set('Content-type', 'application/json')
    })
  }

  updateTodo(todoId : number, todoForm : any) {
    return this.http.put(this.url + '/todo/' + todoId, todoForm, {
      headers : new HttpHeaders().set('Content-type', 'application/json')
    })
  }
}
