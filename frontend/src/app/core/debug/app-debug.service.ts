import { Injectable } from '@angular/core';

@Injectable({providedIn: 'root'})
export class AppDebugService {

  private isDebugEnabled(moduleName: string): boolean {
    return !!localStorage.getItem('debug.' + moduleName);
  }

  public isAuthDebugEnabled(): boolean {
    return this.isDebugEnabled('auth');
  }

}
