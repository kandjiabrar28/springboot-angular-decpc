import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITable, Table } from 'app/shared/model/table.model';
import { TableService } from './table.service';
import { TableComponent } from './table.component';
import { TableDetailComponent } from './table-detail.component';
import { TableUpdateComponent } from './table-update.component';

@Injectable({ providedIn: 'root' })
export class TableResolve implements Resolve<ITable> {
  constructor(private service: TableService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITable> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((table: HttpResponse<Table>) => {
          if (table.body) {
            return of(table.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Table());
  }
}

export const tableRoute: Routes = [
  {
    path: '',
    component: TableComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.table.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TableDetailComponent,
    resolve: {
      table: TableResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.table.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TableUpdateComponent,
    resolve: {
      table: TableResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.table.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TableUpdateComponent,
    resolve: {
      table: TableResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterApp.table.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
