import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IExamen, Examen } from 'app/shared/model/examen.model';
import { ExamenService } from './examen.service';
import { ExamenComponent } from './examen.component';
import { ExamenDetailComponent } from './examen-detail.component';
import { ExamenUpdateComponent } from './examen-update.component';

@Injectable({ providedIn: 'root' })
export class ExamenResolve implements Resolve<IExamen> {
  constructor(private service: ExamenService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExamen> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((examen: HttpResponse<Examen>) => {
          if (examen.body) {
            return of(examen.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Examen());
  }
}

export const examenRoute: Routes = [
  {
    path: '',
    component: ExamenComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.examen.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ExamenDetailComponent,
    resolve: {
      examen: ExamenResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.examen.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ExamenUpdateComponent,
    resolve: {
      examen: ExamenResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.examen.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ExamenUpdateComponent,
    resolve: {
      examen: ExamenResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.examen.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
