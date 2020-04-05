import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { PlageCopieUpdateComponent } from 'app/entities/plage-copie/plage-copie-update.component';
import { PlageCopieService } from 'app/entities/plage-copie/plage-copie.service';
import { PlageCopie } from 'app/shared/model/plage-copie.model';

describe('Component Tests', () => {
  describe('PlageCopie Management Update Component', () => {
    let comp: PlageCopieUpdateComponent;
    let fixture: ComponentFixture<PlageCopieUpdateComponent>;
    let service: PlageCopieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [PlageCopieUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PlageCopieUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlageCopieUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlageCopieService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PlageCopie(123);
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
        const entity = new PlageCopie();
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
