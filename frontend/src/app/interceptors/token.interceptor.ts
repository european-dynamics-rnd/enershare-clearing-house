import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { AuthenticationService } from '../services/system/auth/auth.service';
import { catchError, filter, finalize, switchMap, take } from 'rxjs/operators';
import { Router } from '@angular/router';
import { AuthenticationResponse } from '../dtos/authenticationResponse';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  private isRefreshing = false;
  private tokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

  constructor(private authService: AuthenticationService,
    private router: Router) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if (this.isPublicRoute(request)) {
      return next.handle(request);
    }

    request = this.addToken(request, this.authService.getAccessToken());

    return next.handle(request).pipe(
      catchError((err) => {
        if (err instanceof HttpErrorResponse) {
          if (err.status === 403) {
            return this.handleUnauthorizedError(request, next);
          }
        }
        return throwError(err);
      })
    );
  }

  private addToken(request: HttpRequest<any>, token: string | null): HttpRequest<any> {
    if (token) {
      return request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }
    return request;
  }

  private isPublicRoute(request: HttpRequest<any>): boolean {
    const publicUrls = [
      '/auth/authenticate', 
      '/auth/refresh-token', 
      '/register', 
      '/fetch-available-participants', 
      '/fetch-available-connectors'
    ];
    return publicUrls.some(url => request.url.includes(url));
  }
  private handleUnauthorizedError(request: HttpRequest<any>, next: HttpHandler): Observable<any> {
    if (!this.isRefreshing) {
      this.isRefreshing = true;
      this.tokenSubject.next(null);

      return this.authService.renewToken(this.authService.getRefreshToken()).pipe(
        switchMap((data: AuthenticationResponse) => {
          this.authService.storeRefreshToken(data?.refreshToken);
          this.authService.storeAccessToken(data?.accessToken);
          this.isRefreshing = false;
          this.tokenSubject.next(data?.accessToken);
          return next.handle(this.addToken(request, data?.accessToken));
        }),
        catchError(error => {
          this.authService.logOut();
          this.router.navigateByUrl('/login')
          return throwError(error);
        }),
        finalize(() => {
          this.isRefreshing = false;
        })
      );
    } else {
      return this.tokenSubject.pipe(
        filter(token => token != null),
        take(1),
        switchMap(() => {
          return next.handle(this.addToken(request, this.authService.getAccessToken()));
        })
      );
    }
  }
}
