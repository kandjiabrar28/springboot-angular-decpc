import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { AnonymatUpdateComponent } from 'app/entities/anonymat/anonymat-update.component';
import { AnonymatService } from 'app/entities/anonymat/anonymat.service';
import { Anonymat } from 'app/shared/model/anonymat.model';

describe('Component Tests', () => {
  describe('Anonymat Management Update Component', () => {
    let comp: AnonymatUpdateComponent;
    let fixture: ComponentFixture<AnonymatUpdateComponent>;
    let service: AnonymatService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [AnonymatUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AnonymatUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnonymatUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnonymatService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Anonymat(123);
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
        const entity = new Anonymat();
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
