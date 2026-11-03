import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = (route, state) => {
  console.log('authGuard', sessionStorage);
  const router = inject(Router);
  console.log('authGuard', sessionStorage);

  const token = sessionStorage.getItem('token');
  console.log('token', token);

  if (token) {
    return true;
  }

  return router.createUrlTree(['/login'], {
    queryParams: { returnUrl: state.url },
  });
};
