import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserapiService {

  constructor(private httpCli : HttpClient) { }

  User : any = {};

  create(data : any){
    return this.httpCli.post<any>(`${environment.domain}/user`, data);
  }

  update(data : any){
    return this.httpCli.put<any>(`${environment.domain}/user`, data);
  }

  getById(data : any){

    return this.httpCli.get<any>(`${environment.domain}/user/id/${data}`);
  }

  getByUsername(data : any){
    return this.httpCli.get<any>(`${environment.domain}/user/username/${data.username}`, data);
  }


  getAll(){
    return this.httpCli.get<any>(`${environment.domain}/user`);

  }

}
