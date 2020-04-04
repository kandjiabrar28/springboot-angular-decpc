import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { CorrecteurUpdateComponent } from 'app/entities/correcteur/correcteur-update.component';
import { CorrecteurService } from 'app/entities/correcteur/correcteur.service';
import { Correcteur } from 'app/shared/model/correcteur.model';

describe('Component Tests', () => {
  describe('Correcteur Management Update Component', () => {
    let comp: CorrecteurUpdateComponent;
    let fixture: ComponentFixture<CorrecteurUpdateComponent>;
    let service: CorrecteurService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [CorrecteurUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CorrecteurUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CorrecteurUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CorrecteurService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Correcteur(123);
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
        const entity = new Correcteur();
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
