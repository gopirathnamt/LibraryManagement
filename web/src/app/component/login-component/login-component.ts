import { Component } from '@angular/core';
import { MatFormField, MatInput, MatLabel } from '@angular/material/input';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from '@angular/material/card';
import { MatButton } from '@angular/material/button';
import { ApiService } from '../../service/api-service';
import { catchError, throwError } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { loginSubject } from '../../app';
import { MatSnackBar } from '@angular/material/snack-bar';

export const passwordMatchValidator: ValidatorFn = (
  control: AbstractControl,
): ValidationErrors | null => {
  const password = control.get('password')?.value;
  const retypePassword = control.get('passwordConfirm')?.value;

  return password === retypePassword ? null : { passwordMismatch: true };
};

@Component({
  imports: [
    MatFormField,
    MatLabel,
    MatInput,
    ReactiveFormsModule,
    FormsModule,
    MatCard,
    MatCardHeader,
    MatCardTitle,
    MatCardContent,
    MatButton,
  ],
  selector: 'app-login-component',
  styleUrl: './login-component.css',
  templateUrl: './login-component.html',
})
export class LoginComponent {
  loginGroup: FormGroup = new FormGroup({
    username: new FormControl<string>('', {
      validators: Validators.required,
    }),
    password: new FormControl<string>('', {
      validators: Validators.required,
    }),
  });
  createUserGroup: FormGroup = new FormGroup(
    {
      username: new FormControl<string>('', {
        validators: Validators.required,
      }),
      password: new FormControl<string>('', {
        validators: Validators.required,
      }),
      passwordConfirm: new FormControl<string>('', {
        validators: Validators.required,
      }),
    },
    {
      validators: passwordMatchValidator,
    },
  );
  isLogin: boolean = true;
  public constructor(
    private apiService: ApiService,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar,
  ) {
    console.log('log');
  }

  protected login(event: any) {
    if (this.isLogin) {
      console.log(event);
      this.apiService
        .login(this.loginGroup.getRawValue())
        .pipe(
          catchError((err) => {
            console.log(err);
            this.snackBar.open(err.error, '', {
              verticalPosition: 'top',
            });
            loginSubject.next(false);
            return throwError(() => err);
          }),
        )
        .subscribe((value) => {
          if (value && value.tokenType == 'BASIC') {
            sessionStorage.setItem('token', 'Basic ' + value.token);
          } else {
            sessionStorage.setItem('token', 'Bearer ' + value.token);
          }
          sessionStorage.setItem('username', this.loginGroup.getRawValue().username);
          sessionStorage.setItem('userType', value.userType);

          this.snackBar.open('Login Success', '', {
            verticalPosition: 'top',
          });
          this.router.navigateByUrl("/home");
          loginSubject.next(true);
        });
    } else {
      this.isLogin = true;
    }
  }

  createUser() {
    if (this.isLogin) {
      this.isLogin = false;
    } else {
      this.apiService
        .createUser(this.createUserGroup.getRawValue())
        .pipe(
          catchError((err) => {
            console.log(err);
            return throwError(() => err);
          }),
        )
        .subscribe((_) => {
          this.snackBar.open('User Created successfully', '', {
            verticalPosition: 'top',
            politeness: 'assertive',
          });
          window.location.reload();
        });
    }
  }
}
