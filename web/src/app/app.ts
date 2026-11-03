import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterOutlet } from '@angular/router';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from '@angular/material/card';
import { MatButton } from '@angular/material/button';
import { BehaviorSubject } from 'rxjs';
import { AsyncPipe } from '@angular/common';

export const loginSubject = new BehaviorSubject(sessionStorage.getItem('token') !== null);

@Component({
  imports: [
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatCardTitle,
    RouterOutlet,
    MatButton,
    AsyncPipe,
  ],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App implements OnInit {
  title = 'Library Management';
  isLoggedIn = loginSubject.asObservable();

  constructor(
    private router: Router,
    private route: ActivatedRoute,
  ) {
    this.isLoggedIn.subscribe((isLoggedIn) => {
      console.log(sessionStorage.getItem('token'));
      console.log('Logged in', isLoggedIn);
      if (!isLoggedIn) {
        router.navigate(['/login'], {
          queryParams: {
            returnUrl: router.url,
          },
        });
      } else {

        this.router.navigateByUrl("/home");
      }
    });
  }

  ngOnInit() {
    // let token = sessionStorage.getItem('token');
    // let login = token !== null;
    // loginSubject.next(login);
  }

  protected logout() {
    sessionStorage.clear();
    loginSubject.next(false);
  }
}
