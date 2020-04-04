import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IJury, Jury } from 'app/shared/model/jury.model';
import { JuryService } from './jury.service';
import { JuryComponent } from './jury.component';
import { JuryDetailComponent } from './jury-detail.component';
import { JuryUpdateComponent } from './jury-update.component';

@Injectable({ providedIn: 'root' })
export class JuryResolve implements Resolve<IJury> {
  constructor(private service: JuryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJury> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((jury: HttpResponse<Jury>) => {
          if (jury.body) {
            return of(jury.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Jury());
  }
}

export const juryRoute: Routes = [
  {
    path: '',
    component: JuryComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.jury.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: JuryDetailComponent,
    resolve: {
      jury: JuryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.jury.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: JuryUpdateComponent,
    resolve: {
      jury: JuryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.jury.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: JuryUpdateComponent,
    resolve: {
      jury: JuryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.jury.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
