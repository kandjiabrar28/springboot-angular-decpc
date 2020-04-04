import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITour, Tour } from 'app/shared/model/tour.model';
import { TourService } from './tour.service';
import { TourComponent } from './tour.component';
import { TourDetailComponent } from './tour-detail.component';
import { TourUpdateComponent } from './tour-update.component';

@Injectable({ providedIn: 'root' })
export class TourResolve implements Resolve<ITour> {
  constructor(private service: TourService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITour> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tour: HttpResponse<Tour>) => {
          if (tour.body) {
            return of(tour.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Tour());
  }
}

export const tourRoute: Routes = [
  {
    path: '',
    component: TourComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.tour.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TourDetailComponent,
    resolve: {
      tour: TourResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.tour.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TourUpdateComponent,
    resolve: {
      tour: TourResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.tour.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TourUpdateComponent,
    resolve: {
      tour: TourResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.tour.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
