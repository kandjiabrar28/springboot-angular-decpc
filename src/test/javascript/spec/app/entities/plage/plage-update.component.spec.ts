import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { PlageUpdateComponent } from 'app/entities/plage/plage-update.component';
import { PlageService } from 'app/entities/plage/plage.service';
import { Plage } from 'app/shared/model/plage.model';

describe('Component Tests', () => {
  describe('Plage Management Update Component', () => {
    let comp: PlageUpdateComponent;
    let fixture: ComponentFixture<PlageUpdateComponent>;
    let service: PlageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [PlageUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PlageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlageUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlageService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Plage(123);
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
        const entity = new Plage();
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
