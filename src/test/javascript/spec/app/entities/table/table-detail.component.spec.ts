import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { TableDetailComponent } from 'app/entities/table/table-detail.component';
import { Table } from 'app/shared/model/table.model';

describe('Component Tests', () => {
  describe('Table Management Detail Component', () => {
    let comp: TableDetailComponent;
    let fixture: ComponentFixture<TableDetailComponent>;
    const route = ({ data: of({ table: new Table(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [TableDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TableDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TableDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load table on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.table).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
