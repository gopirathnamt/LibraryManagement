import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable,
  MatTableDataSource,
} from '@angular/material/table';
import { YesNoPipe } from '../../utility/yes-or-no.pipe';
import { MatButton } from '@angular/material/button';
import { ApiService } from '../../service/api-service';
import { MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { catchError, throwError } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';

export interface BookElement {
  name: string;
  borrowed: boolean;
  waitlist: boolean;
  available: boolean;
}

@Component({
  imports: [
    MatPaginator,
    MatTable,
    MatHeaderCell,
    MatColumnDef,
    MatCell,
    MatCellDef,
    MatHeaderCellDef,
    MatHeaderRow,
    MatHeaderRowDef,
    MatRow,
    MatRowDef,
    YesNoPipe,
    MatButton,
    MatFormField,
    MatLabel,
    MatInput,
    ReactiveFormsModule,
  ],
  selector: 'app-home-component',
  styleUrl: './home-component.css',
  templateUrl: './home-component.html',
})
export class HomeComponent implements AfterViewInit, OnInit {
  displayedColumns = ['id', 'name', 'borrowed'];
  dataSource = new MatTableDataSource<BookElement>();
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  userType: string | null = null;
  bookNameControl = new FormControl<string>('', Validators.required);

  constructor(
    private apiService: ApiService,
    private snackBar: MatSnackBar,
  ) {
    console.log('Home Component');
    this.userType = sessionStorage.getItem('userType');
    if (this.userType === 'USER') {
      this.displayedColumns.push('waitlist');
      this.displayedColumns.push('options');
    }
  }

  ngOnInit() {
    this.apiService
      .list()
      .pipe(
        catchError((err) => {
          console.log(err);
          return throwError(() => err);
        }),
      )
      .subscribe((response) => {
        console.log(response);
        if (response !== null) {
          this.dataSource.data = response;
        }
      });
  }

  ngAfterViewInit() {
    console.log('ngAfterViewInit');
    console.log('paginator', this.paginator);
    this.dataSource.paginator = this.paginator;
  }

  borrow(element: BookElement) {
    console.log('borrow');
    this.apiService.borrow(element).subscribe((response) => {
      console.log(response);
      this.snackBar.open('Book Borrowed successfully', '', {
        verticalPosition: 'top',
      });
      this.dataSource.data = this.dataSource.data.map((item) =>
        item.name === response.name ? response : item,
      );
    });
  }

  waitlist(element: BookElement) {
    this.apiService.waitlist(element).subscribe((response) => {
      console.log(response);
      this.snackBar.open('Book added to Waitlist successfully', '', {
        verticalPosition: 'top',
      });
      this.dataSource.data = this.dataSource.data.map((item) =>
        item.name === response.name ? response : item,
      );
    });
  }

  protected returnBook(element: BookElement) {
    this.apiService.returnBook(element).subscribe((response) => {
      console.log(response);
      this.snackBar.open('Book Returned successfully', '', {
        verticalPosition: 'top',
        politeness: 'assertive',
      });
      element = response;
      this.dataSource.data = this.dataSource.data.map((item) =>
        item.name === response.name ? response : item,
      );
    });
  }

  protected addBook() {
    this.apiService
      .addBook(this.bookNameControl.value)
      .pipe(
        catchError((err) => {
          console.log(err);
          return throwError(() => err);
        }),
      )
      .subscribe((response) => {
        console.log(response);
        if (response !== null) {
          this.dataSource.data = response;
        }
      });
  }
}
