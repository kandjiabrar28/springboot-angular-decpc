import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SurveillantService } from 'app/entities/surveillant/surveillant.service';
import { ISurveillant, Surveillant } from 'app/shared/model/surveillant.model';

describe('Service Tests', () => {
  describe('Surveillant Service', () => {
    let injector: TestBed;
    let service: SurveillantService;
    let httpMock: HttpTestingController;
    let elemDefault: ISurveillant;
    let expectedResult: ISurveillant | ISurveillant[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SurveillantService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Surveillant(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            datenais: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Surveillant', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            datenais: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datenais: currentDate
          },
          returnedFromService
        );

        service.create(new Surveillant()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Surveillant', () => {
        const returnedFromService = Object.assign(
          {
            fonction: 'BBBBBB',
            prenom: 'BBBBBB',
            nom: 'BBBBBB',
            provenance: 'BBBBBB',
            cni: 'BBBBBB',
            telephone: 'BBBBBB',
            sexe: 'BBBBBB',
            datenais: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datenais: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Surveillant', () => {
        const returnedFromService = Object.assign(
          {
            fonction: 'BBBBBB',
            prenom: 'BBBBBB',
            nom: 'BBBBBB',
            provenance: 'BBBBBB',
            cni: 'BBBBBB',
            telephone: 'BBBBBB',
            sexe: 'BBBBBB',
            datenais: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datenais: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Surveillant', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
