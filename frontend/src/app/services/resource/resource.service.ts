import { Injectable } from '@angular/core';
import {SearchRequest} from "../../dtos/searchRequest";
import {Observable} from "rxjs";
import {Page} from "../../dtos/page";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Resources} from "../../dtos/resources";

@Injectable({
  providedIn: 'root'
})
export class ResourceService {

  constructor(private http: HttpClient) { }

  getSearchResources(searchRequest: SearchRequest): Observable<Page<Resources>> {
    return this.http.post<Page<Resources>>(`${environment.serverUrl}/resources/search`, searchRequest);
  }

  getFreeResources(): Observable<Resources[]> {
    return this.http.get<Resources[]>(`${environment.serverUrl}/resources/free`);
  }
}
