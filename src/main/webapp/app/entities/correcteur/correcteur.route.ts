import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICorrecteur, Correcteur } from 'app/shared/model/correcteur.model';
import { CorrecteurService } from './correcteur.service';
import { CorrecteurComponent } from './correcteur.component';
import { CorrecteurDetailComponent } from './correcteur-detail.component';
import { CorrecteurUpdateComponent } from './correcteur-update.component';

@Injectable({ providedIn: 'root' })
export class CorrecteurResolve implements Resolve<ICorrecteur> {
  constructor(private service: CorrecteurService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICorrecteur> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((correcteur: HttpResponse<Correcteur>) => {
          if (correcteur.body) {
            return of(correcteur.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Correcteur());
  }
}

export const correcteurRoute: Routes = [
  {
    path: '',
    component: CorrecteurComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.correcteur.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CorrecteurDetailComponent,
    resolve: {
      correcteur: CorrecteurResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.correcteur.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CorrecteurUpdateComponent,
    resolve: {
      correcteur: CorrecteurResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.correcteur.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CorrecteurUpdateComponent,
    resolve: {
      correcteur: CorrecteurResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.correcteur.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
