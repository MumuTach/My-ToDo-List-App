import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { User } from '../shared/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url = 'http://localhost:8000';

  constructor(private http : HttpClient) { }

  login(data : any) {
    return this.http.post(this.url + '/user/login', data, 
      {
        headers : new HttpHeaders().set('Content-type', 'application/json')
      }
    );
  }

  signUp(data : any) {
    return this.http.post(this.url + '/user/signUp', data, 
      {
        headers : new HttpHeaders().set('Content-type', 'application/json')
      }
    );
  }

  logOut() {
    return this.http.post(this.url + '/user/logout', null, {
      headers : new HttpHeaders().set('Content-type', 'application/json')
    });
  }

  getUser(email : string) {
    return this.http.get<User>(this.url + '/user/email/' + email, {
      headers : new HttpHeaders().set('Content-type', 'application/json')
    })
  }

  getCurrentUserId(): number | null {
    const userId = localStorage.getItem('userId');
    return userId ? parseInt(userId, 10) : null; 
  }
}
