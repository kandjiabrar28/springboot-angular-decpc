import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITAnonym, TAnonym } from 'app/shared/model/t-anonym.model';
import { TAnonymService } from './t-anonym.service';
import { TAnonymComponent } from './t-anonym.component';
import { TAnonymDetailComponent } from './t-anonym-detail.component';
import { TAnonymUpdateComponent } from './t-anonym-update.component';

@Injectable({ providedIn: 'root' })
export class TAnonymResolve implements Resolve<ITAnonym> {
  constructor(private service: TAnonymService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITAnonym> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tAnonym: HttpResponse<TAnonym>) => {
          if (tAnonym.body) {
            return of(tAnonym.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TAnonym());
  }
}

export const tAnonymRoute: Routes = [
  {
    path: '',
    component: TAnonymComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.tAnonym.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TAnonymDetailComponent,
    resolve: {
      tAnonym: TAnonymResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.tAnonym.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TAnonymUpdateComponent,
    resolve: {
      tAnonym: TAnonymResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.tAnonym.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TAnonymUpdateComponent,
    resolve: {
      tAnonym: TAnonymResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.tAnonym.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
