import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Page} from "../../dtos/page";
import {User} from "../../dtos/user";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private http: HttpClient,) { }

    getUsers(page: number, pageSize: number): Observable<Page<User>> {
    const url = `${environment.serverUrl}/user/all-users?page=${page}&size=${pageSize}`;
        return this.http.get<Page<User>>(url);
  }

  getTotalUsers(): Observable<number> {
    return this.http.get<number>(`${environment.serverUrl}/user/total-users`);
  }

  createUser(user){
    return this.http.post(environment.serverUrl + '/user/create',user)
  }

  updateUser(user){
    return this.http.put(environment.serverUrl + '/user/update',user)
  }

  delete(id: string) {
    return this.http.delete(`${environment.serverUrl}/user?id=${id}`);
  }

  getUserById(id: string){
    return this.http.get(`${environment.serverUrl}/user/by-id?id=${id}`)
  }
}


