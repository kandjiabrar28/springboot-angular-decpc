import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlage, Plage } from 'app/shared/model/plage.model';
import { PlageService } from './plage.service';
import { PlageComponent } from './plage.component';
import { PlageDetailComponent } from './plage-detail.component';
import { PlageUpdateComponent } from './plage-update.component';

@Injectable({ providedIn: 'root' })
export class PlageResolve implements Resolve<IPlage> {
  constructor(private service: PlageService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlage> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((plage: HttpResponse<Plage>) => {
          if (plage.body) {
            return of(plage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Plage());
  }
}

export const plageRoute: Routes = [
  {
    path: '',
    component: PlageComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.plage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PlageDetailComponent,
    resolve: {
      plage: PlageResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.plage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PlageUpdateComponent,
    resolve: {
      plage: PlageResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.plage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PlageUpdateComponent,
    resolve: {
      plage: PlageResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.plage.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
