import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DarkmodeService {
  isDarkMode : boolean = false;

  constructor() { }

  toggleDarkMode(){
    this.isDarkMode = !this.isDarkMode;
    return this.isDarkMode;
  }

  checkDarkMode(){
    return this.isDarkMode;
  }
}
