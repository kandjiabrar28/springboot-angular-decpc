import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestModule } from '../../../test.module';
import { TableComponent } from 'app/entities/table/table.component';
import { TableService } from 'app/entities/table/table.service';
import { Table } from 'app/shared/model/table.model';

describe('Component Tests', () => {
  describe('Table Management Component', () => {
    let comp: TableComponent;
    let fixture: ComponentFixture<TableComponent>;
    let service: TableService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [TableComponent]
      })
        .overrideTemplate(TableComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TableComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TableService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Table(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tables && comp.tables[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
