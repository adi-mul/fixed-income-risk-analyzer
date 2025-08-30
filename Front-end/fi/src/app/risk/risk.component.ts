import { Component } from '@angular/core';



import { AfterViewInit,OnInit,ViewChild } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { MatPaginatorModule, MatPaginator} from '@angular/material/paginator';

import { MatTableDataSource } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { CanvasJSAngularChartsModule } from '@canvasjs/angular-charts';
import { FormsModule } from '@angular/forms';
import { log } from 'node:console';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-risk',
  standalone: true,
  imports: [MatTableModule,MatSortModule,MatPaginatorModule,MatFormFieldModule,MatInputModule,CanvasJSAngularChartsModule,FormsModule,CommonModule],
  templateUrl: './risk.component.html',
  styleUrl: './risk.component.css'
})
export class RiskComponent {
  risk_get_data:any=[];
  risk_store_responce:any;
  risk_store_array:any=[];
  bond_id:any

   constructor( private http:HttpClient){}
     
    dataSource:any;
      @ViewChild(MatPaginator) paginator !:MatPaginator

   ngAfterViewInit():void{
      this.http.get("http://localhost:9191/fi/risk_table").subscribe(data=>{
        this.risk_get_data=data
        this.dataSource= new MatTableDataSource(this.risk_get_data)
        this.dataSource.paginator=this.paginator
    })}

    get_risk_data(event :any,bond_id: number){
       console.log(bond_id);  
      return  this.http.get(`http://localhost:9191/fi/risk_aasessment/`+bond_id).subscribe(responce=>{
       this.risk_store_responce=responce;
       console.log(responce);
       
       this.risk_store_responce.array.forEach((element:any) => {
        this.risk_store_array.add(element)
       });
   })
  }

    doFilter = (event:Event) => {
      const Filtervalue =(event.target as HTMLInputElement).value
      this.dataSource.filter = Filtervalue.trim().toLocaleLowerCase();
    }


    
    displayedColumns = ['Id','Bond name','Issuer','Face value','Coupon rate','Maturity date','Currency','Credit rating','Coupon freq','Bid price','Ask price','Bond volume','Total outstanding','Risk'];



    
}
