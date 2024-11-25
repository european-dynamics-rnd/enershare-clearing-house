import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Page } from '../../dtos/page';
import { User } from '../../dtos/user';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private http: HttpClient) { }

  getUsers(page: number, pageSize: number): Observable<Page<User>> {
    const url = `${environment.serverUrl}/user/all-users?page=${page}&size=${pageSize}`;
    return this.http.get<Page<User>>(url);
  }

  getTotalUsers(): Observable<number> {
    return this.http.get<number>(`${environment.serverUrl}/user/total-users`);
  }

  createUser(user): Observable<any> {
    return this.http.post(`${environment.serverUrl}/user/create`, user);
  }

  updateUser(user): Observable<any> {
    return this.http.put(`${environment.serverUrl}/user/update`, user);
  }

  delete(id: string): Observable<any> {
    return this.http.delete(`${environment.serverUrl}/user?id=${id}`);
  }

  getUserById(id: string): Observable<any> {
    return this.http.get(`${environment.serverUrl}/user/by-id?id=${id}`);
  }

  registerUser(user: any): Observable<any> {

    return this.http.post(`${environment.serverUrl}/user/register-user`, user).pipe(
      map((response: any) => {

        return response;
      }),
      catchError((errorReponse: HttpErrorResponse) => {
        console.error('Registration error:');
        console.log(errorReponse.error.code);

        const errorMessage = errorReponse.error?.message || 'Registration failed. Please try again later.';
        return of({ error: { message: errorMessage } });
      })
    );
  }

  getAvailableParticipants(): Observable<any> {
    return this.http.get(`${environment.serverUrl}/user/fetch-available-participants`);
  }

  getConnectorsForSelectedParticipant(selectedParticipantId: string ): Observable<any> {
    return this.http.get(`${environment.serverUrl}/user/fetch-available-connectors/${selectedParticipantId}`);
  }
}
