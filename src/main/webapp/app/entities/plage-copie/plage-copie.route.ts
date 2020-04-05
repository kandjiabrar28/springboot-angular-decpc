import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlageCopie, PlageCopie } from 'app/shared/model/plage-copie.model';
import { PlageCopieService } from './plage-copie.service';
import { PlageCopieComponent } from './plage-copie.component';
import { PlageCopieDetailComponent } from './plage-copie-detail.component';
import { PlageCopieUpdateComponent } from './plage-copie-update.component';

@Injectable({ providedIn: 'root' })
export class PlageCopieResolve implements Resolve<IPlageCopie> {
  constructor(private service: PlageCopieService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlageCopie> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((plageCopie: HttpResponse<PlageCopie>) => {
          if (plageCopie.body) {
            return of(plageCopie.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PlageCopie());
  }
}

export const plageCopieRoute: Routes = [
  {
    path: '',
    component: PlageCopieComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.plageCopie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PlageCopieDetailComponent,
    resolve: {
      plageCopie: PlageCopieResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.plageCopie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PlageCopieUpdateComponent,
    resolve: {
      plageCopie: PlageCopieResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.plageCopie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PlageCopieUpdateComponent,
    resolve: {
      plageCopie: PlageCopieResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.plageCopie.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
