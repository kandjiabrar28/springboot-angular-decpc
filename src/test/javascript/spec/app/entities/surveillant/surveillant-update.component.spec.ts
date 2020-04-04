import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { SurveillantUpdateComponent } from 'app/entities/surveillant/surveillant-update.component';
import { SurveillantService } from 'app/entities/surveillant/surveillant.service';
import { Surveillant } from 'app/shared/model/surveillant.model';

describe('Component Tests', () => {
  describe('Surveillant Management Update Component', () => {
    let comp: SurveillantUpdateComponent;
    let fixture: ComponentFixture<SurveillantUpdateComponent>;
    let service: SurveillantService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [SurveillantUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SurveillantUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SurveillantUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SurveillantService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Surveillant(123);
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
        const entity = new Surveillant();
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
