import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { TAnonymUpdateComponent } from 'app/entities/t-anonym/t-anonym-update.component';
import { TAnonymService } from 'app/entities/t-anonym/t-anonym.service';
import { TAnonym } from 'app/shared/model/t-anonym.model';

describe('Component Tests', () => {
  describe('TAnonym Management Update Component', () => {
    let comp: TAnonymUpdateComponent;
    let fixture: ComponentFixture<TAnonymUpdateComponent>;
    let service: TAnonymService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [TAnonymUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TAnonymUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TAnonymUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TAnonymService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TAnonym(123);
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
        const entity = new TAnonym();
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
