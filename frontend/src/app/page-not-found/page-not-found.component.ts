import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.css']
})
export class PageNotFoundComponent implements OnInit {

  private tools: string[] = ['hammer', 'pliers', 'saw', 'screwdriver'];

  private tool;

  constructor() { }

  ngOnInit() {
    this.chooseRandomTool();
  }

  chooseRandomTool(): void {
    const items = this.tools.length;
    const randItemIndex = Math.floor(Math.random() * items);
    this.tool = this.tools[randItemIndex];
  }

}
