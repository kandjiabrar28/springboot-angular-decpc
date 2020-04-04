import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { JuryUpdateComponent } from 'app/entities/jury/jury-update.component';
import { JuryService } from 'app/entities/jury/jury.service';
import { Jury } from 'app/shared/model/jury.model';

describe('Component Tests', () => {
  describe('Jury Management Update Component', () => {
    let comp: JuryUpdateComponent;
    let fixture: ComponentFixture<JuryUpdateComponent>;
    let service: JuryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [JuryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(JuryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JuryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JuryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Jury(123);
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
        const entity = new Jury();
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
