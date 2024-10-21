import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  signUpForm : FormGroup;
  hide: boolean = true;


  constructor(private userService : UserService, private toaster : ToastrService, private route : Router){
    this.signUpForm = new FormGroup({
      name : new FormControl('', [Validators.required]),
      email : new FormControl('', [Validators.required, Validators.email]),
      password : new FormControl('', [Validators.required, Validators.minLength(8)]),
    })
  }

  ngOnInit(): void {}

  onSubmit() {
    if (this.signUpForm.valid) {

      const signUpData = this.signUpForm.value;
      
      this.userService.signUp(signUpData).subscribe({
        next: (response : any) => {
          console.log('Response from API:', response);

          if (response.success) {
            this.toaster.success(response.message, "Success");
            this.route.navigate(['/login']); 
          } else {
          this.toaster.error(response.message, "SignUp Error");
        }
        },
        error: (err : any) => {
          this.toaster.error('Error when signing up', 'Try again');
          console.error('Error during sign-up:', err);
        }
      });
    } else {
      this.toaster.error('Please fill out the form correctly', 'Form Error');
    }
  }
}
