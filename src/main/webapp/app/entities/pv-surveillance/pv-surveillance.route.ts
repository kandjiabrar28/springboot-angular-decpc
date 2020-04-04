import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPVSurveillance, PVSurveillance } from 'app/shared/model/pv-surveillance.model';
import { PVSurveillanceService } from './pv-surveillance.service';
import { PVSurveillanceComponent } from './pv-surveillance.component';
import { PVSurveillanceDetailComponent } from './pv-surveillance-detail.component';
import { PVSurveillanceUpdateComponent } from './pv-surveillance-update.component';

@Injectable({ providedIn: 'root' })
export class PVSurveillanceResolve implements Resolve<IPVSurveillance> {
  constructor(private service: PVSurveillanceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPVSurveillance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pVSurveillance: HttpResponse<PVSurveillance>) => {
          if (pVSurveillance.body) {
            return of(pVSurveillance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PVSurveillance());
  }
}

export const pVSurveillanceRoute: Routes = [
  {
    path: '',
    component: PVSurveillanceComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.pVSurveillance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PVSurveillanceDetailComponent,
    resolve: {
      pVSurveillance: PVSurveillanceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.pVSurveillance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PVSurveillanceUpdateComponent,
    resolve: {
      pVSurveillance: PVSurveillanceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.pVSurveillance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PVSurveillanceUpdateComponent,
    resolve: {
      pVSurveillance: PVSurveillanceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.pVSurveillance.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
