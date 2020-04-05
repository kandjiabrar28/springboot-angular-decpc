import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { TableUpdateComponent } from 'app/entities/table/table-update.component';
import { TableService } from 'app/entities/table/table.service';
import { Table } from 'app/shared/model/table.model';

describe('Component Tests', () => {
  describe('Table Management Update Component', () => {
    let comp: TableUpdateComponent;
    let fixture: ComponentFixture<TableUpdateComponent>;
    let service: TableService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [TableUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TableUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TableUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TableService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Table(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Table();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
