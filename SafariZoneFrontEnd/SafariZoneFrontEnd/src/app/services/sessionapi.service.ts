import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SessionapiService {

  constructor(private httpCli : HttpClient) { }

  login(user : any){
    return this.httpCli.post<any>(`${environment.domain}/session`,user ,
    {
      withCredentials: true
    });
  }

  checkSession(){
    return this.httpCli.get<any>(`${environment.domain}/session`, {
      withCredentials: true
    });
  }

  logout(){
    return this.httpCli.delete<any>(`${environment.domain}/session`, {
      withCredentials: true
    });
  }
}
