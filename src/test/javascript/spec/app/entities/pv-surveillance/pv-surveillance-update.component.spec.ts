import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { PVSurveillanceUpdateComponent } from 'app/entities/pv-surveillance/pv-surveillance-update.component';
import { PVSurveillanceService } from 'app/entities/pv-surveillance/pv-surveillance.service';
import { PVSurveillance } from 'app/shared/model/pv-surveillance.model';

describe('Component Tests', () => {
  describe('PVSurveillance Management Update Component', () => {
    let comp: PVSurveillanceUpdateComponent;
    let fixture: ComponentFixture<PVSurveillanceUpdateComponent>;
    let service: PVSurveillanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [PVSurveillanceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PVSurveillanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PVSurveillanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PVSurveillanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PVSurveillance(123);
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
        const entity = new PVSurveillance();
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
