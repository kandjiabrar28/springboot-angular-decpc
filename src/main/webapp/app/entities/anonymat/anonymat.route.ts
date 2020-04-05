import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAnonymat, Anonymat } from 'app/shared/model/anonymat.model';
import { AnonymatService } from './anonymat.service';
import { AnonymatComponent } from './anonymat.component';
import { AnonymatDetailComponent } from './anonymat-detail.component';
import { AnonymatUpdateComponent } from './anonymat-update.component';

@Injectable({ providedIn: 'root' })
export class AnonymatResolve implements Resolve<IAnonymat> {
  constructor(private service: AnonymatService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnonymat> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((anonymat: HttpResponse<Anonymat>) => {
          if (anonymat.body) {
            return of(anonymat.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Anonymat());
  }
}

export const anonymatRoute: Routes = [
  {
    path: '',
    component: AnonymatComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.anonymat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnonymatDetailComponent,
    resolve: {
      anonymat: AnonymatResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.anonymat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnonymatUpdateComponent,
    resolve: {
      anonymat: AnonymatResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.anonymat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnonymatUpdateComponent,
    resolve: {
      anonymat: AnonymatResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.anonymat.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
