import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm : FormGroup;
  hide: boolean = true;
  
  constructor(private userService : UserService, private toaster : ToastrService, private route : Router){
    this.loginForm = new FormGroup({
      email : new FormControl('', [Validators.required, Validators.email]),
      password : new FormControl('', [Validators.required, Validators.minLength(8)]),
    })
  }

  ngOnInit(): void {}

  onSubmit() {
    if (this.loginForm.valid) {

      const loginData = this.loginForm.value;
      
      this.userService.login(loginData).subscribe({
        next: (response : any) => {
          if (response.success) {
            localStorage.setItem('userEmail', loginData.email);
            localStorage.setItem('userId', response.user.id.toString());
            this.toaster.success('login successful', 'Welcome!');
            this.route.navigate(['/todo']).then(() => {
              window.location.reload();
            });; 
          } else {
            this.toaster.error('Email or password incorrect', 'Connexion error');
          }
        },
        error: (err : any) => {
          this.toaster.error('Error when connecting', 'Try again');
          console.error(err);
        }
      });
    } else {
      this.toaster.error('Please fill out the form correctly', 'Error');
    }
  }

}
