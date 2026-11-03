import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { BookElement } from '../component/home-component/home-component';
import { MatDialog } from '@angular/material/dialog';

export interface LoginResult {
  token: string;
  tokenType: 'BASIC' | 'BEARER';
  userType: 'ADMIN' | 'USER';
}

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  public constructor(
    private http: HttpClient,
    private dialog: MatDialog,
  ) {}

  public login(request: any): Observable<LoginResult> {
    return this.http.post<LoginResult>('http://localhost:8080/api/login', request, {
      responseType: 'json',
    });
  }

  borrow(element: BookElement) {
    let token = sessionStorage.getItem('token');

    return this.http
      .get<BookElement>('http://localhost:8080/api/book/borrow', {
        responseType: 'json',
        params: {
          bookName: element.name,
        },
        headers: { Authorization: `${token}` },
      })
      .pipe(
        catchError((err) => {
          console.log(err);
          return throwError(() => err);
        }),
      );
  }

  addBook(bookName: string | null) {
    let token = sessionStorage.getItem('token');

    return this.http.get<BookElement[]>('http://localhost:8080/api/book/add', {
      responseType: 'json',
      params: {
        bookName: bookName || '',
      },
      headers: { Authorization: `${token}` },
    });
  }

  list() {
    let token = sessionStorage.getItem('token');

    return this.http.get<BookElement[]>('http://localhost:8080/api/book/list', {
      responseType: 'json',
      headers: { Authorization: `${token}` },
    });
  }

  public createUser(request: any): Observable<String> {
    return this.http.post<String>('http://localhost:8080/api/login/create', request, {
      responseType: 'text',
    });
  }

  waitlist(element: BookElement) {
    let token = sessionStorage.getItem('token');

    return this.http
      .get<BookElement>('http://localhost:8080/api/book/wait-list', {
        responseType: 'json',
        params: {
          bookName: element.name,
        },
        headers: { Authorization: `${token}` },
      })
      .pipe(
        catchError((err) => {
          console.log(err);
          return throwError(() => err);
        }),
      );
  }

  returnBook(element: BookElement) {
    let token = sessionStorage.getItem('token');

    return this.http
      .get<BookElement>('http://localhost:8080/api/book/return', {
        responseType: 'json',
        params: {
          bookName: element.name,
        },
        headers: { Authorization: `${token}` },
      })
      .pipe(
        catchError((err) => {
          console.log(err);
          return throwError(() => err);
        }),
      );
  }
}
