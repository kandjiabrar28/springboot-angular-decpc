import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISurveillant, Surveillant } from 'app/shared/model/surveillant.model';
import { SurveillantService } from './surveillant.service';
import { SurveillantComponent } from './surveillant.component';
import { SurveillantDetailComponent } from './surveillant-detail.component';
import { SurveillantUpdateComponent } from './surveillant-update.component';

@Injectable({ providedIn: 'root' })
export class SurveillantResolve implements Resolve<ISurveillant> {
  constructor(private service: SurveillantService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISurveillant> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((surveillant: HttpResponse<Surveillant>) => {
          if (surveillant.body) {
            return of(surveillant.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Surveillant());
  }
}

export const surveillantRoute: Routes = [
  {
    path: '',
    component: SurveillantComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.surveillant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SurveillantDetailComponent,
    resolve: {
      surveillant: SurveillantResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.surveillant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SurveillantUpdateComponent,
    resolve: {
      surveillant: SurveillantResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.surveillant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SurveillantUpdateComponent,
    resolve: {
      surveillant: SurveillantResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.surveillant.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
