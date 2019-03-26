import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from "@angular/router";

@Component({
  selector: 'login-tacocloud',
  templateUrl: 'login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
  model = {
    username: null,
    password: null
  };

  constructor(private httpClient: HttpClient, private router: Router) {

  }

  ngOnInit() {
  }

  signIn() {
    this.httpClient.post('http://localhost:8080/login', JSON.stringify(this.model))
      .subscribe();
    this.router.navigate(['/home']);
  }
}
