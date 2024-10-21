import { Component, OnInit } from '@angular/core';
import { UserService } from './services/user.service';
import { ToastrService } from 'ngx-toastr';
import { NavigationEnd, Route, Router } from '@angular/router';
import { User } from './shared/user';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'myToDoList';
  user: User | null = null;

  constructor(private userService : UserService, private toaster: ToastrService, private route: Router) {}

  ngOnInit(): void {
    const email = localStorage.getItem('userEmail');
    this.route.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        if (event.url === '/login'|| event.url === '/signUp') {
          this.user = undefined as any;
        } else if (email) {
          this.getUserByEmail(email);
        }
      }
    });
  }

  getUserByEmail(email : string) {
    return this.userService.getUser(email).subscribe({
      next: (response: User) => {
        this.user = response;
      },
      error: (err: any) => {
        this.toaster.error('Error retrieving user', 'Try again');
        console.error(err);
      }
    });;
  }

  onLogout() {
    this.userService.logOut().subscribe({
      next: () => {
        this.toaster.success('Logged out successfully');
        localStorage.removeItem('userEmail');
        this.user = null;
        this.route.navigate(['/login']);
      },
      error: (err: any) => {
        console.error('Error during logout:', err);
        this.toaster.error('Error during logout', 'Try again');
        console.error(err);
      },
      complete: () => {
        console.log('Logout request completed'); 
      }
    });
  }
}
