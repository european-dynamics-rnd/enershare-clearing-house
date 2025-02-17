import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {AuthenticationResponse} from "../../../dtos/authenticationResponse";
import {environment} from "../../../../environments/environment";
import {BehaviorSubject, Observable, throwError} from "rxjs";
import {User} from "../../../dtos/user";



@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  public isTokenRenewing = false;
  // Define token renewal queue as a BehaviorSubject
  private _tokenRenewalQueue: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  // Expose token renewal queue as an Observable
  public tokenRenewalQueue: Observable<boolean> = this._tokenRenewalQueue.asObservable();

  // Method to trigger token renewal completion and notify subscribers
  public setTokenRenewalComplete(): void {
    this._tokenRenewalQueue.next(true);
  }

  // Method to reset token renewal status
  public resetTokenRenewalStatus(): void {
    this._tokenRenewalQueue.next(false);
  }
  constructor(private http: HttpClient) { }


  onLogin(username: string, password: string) {
    return this.http.post(`${environment.serverUrl}/auth/authenticate`, { username, password });

  }

  logOut(){
    return this.http.get(environment.serverUrl +'/auth/logout').subscribe(res=>{
      localStorage.removeItem('accessToken');
      localStorage.removeItem('refreshToken');
    });

  }

  getCurrentUser(): any {
    const currentUserString = localStorage.getItem('user');
    if (currentUserString) {
      return JSON.parse(currentUserString);
    } else {
      return null;
    }
  }

  getCurrentUserName(): string | null {
    const currentUser = this.getCurrentUser();
    return currentUser ? currentUser?.username: null;
  }

  getLogs(){
    return this.http.get(environment.serverUrl + '/logs')

  }

  storeUserRole(role: string) {
    localStorage.setItem('userRole', role);
  }

  // Method to retrieve the user role from local storage
  getUserRole(): string | null {
    return localStorage.getItem('userRole');
  }

  storeUser(user: User): void {
    // Example: Store the user DTO in the local storage
    localStorage.setItem('user',JSON.stringify(user));
  }

  storeAccessToken(tokenValue:string){
    localStorage.setItem('accessToken',tokenValue)
  }

  storeRefreshToken(tokenValue:string){
    localStorage.setItem('refreshToken',tokenValue)
  }

  getAccessToken(){
    return localStorage.getItem('accessToken')
  }

  getRefreshToken(){
    return localStorage.getItem('refreshToken')
  }

  isLoggedIn():boolean{
    return !!localStorage.getItem('accessToken')
  }

  renewToken(refreshToken : String){

    const requestOptions: Object = {
      headers: { Authorization: `Bearer ${refreshToken}` },
    };

    return this.http.post<any>(environment.serverUrl + '/auth/refresh-token',null,requestOptions);
  }


}
