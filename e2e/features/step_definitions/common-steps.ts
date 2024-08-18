import { Given, Then, When } from '@cucumber/cucumber'
import { expect } from 'chai';
import { JSONPath } from 'jsonpath-plus'

const BASE_URL = process.env.BASE_URL || 'http://localhost:8080';

interface ThisWorld {
    requestBody: any;
    params: any;
    response: Response;
}

Given('RequestBodyの {string} に {string} を設定', async function (this: ThisWorld, keyName, value) {
    // Write code here that turns the phrase above into concrete actions
    this.requestBody = this.requestBody || {};
    this.requestBody[keyName] = value;
});

When('{string} にPUTリクエストを送信', async function (this, url) {
    this.response = await fetch(BASE_URL + url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(this.requestBody),
    });
    this.requestBody = await this.response.json();
});

Then('ステータスコードが200であること', async function () {
    expect(this.response.status).to.equal(200);
});

Then('ResponseBodyの {string} がnullではないこと', async function (path) {
    const value = JSONPath({ json: this.requestBody, path });
    expect(value).to.not.be.null;
});

Then('ResponseBodyの {string} が {string} であること', async function (path, expectValue) {
    const value = JSONPath({ json: this.requestBody, path });
    expect(value[0]).to.equal(expectValue);
});

When('{string} にGETリクエストを送信', async function (url) {
    this.params = this.params || {};
    const urlParams = new URLSearchParams(this.params);
    this.response = await fetch(BASE_URL + url + '?' + urlParams.toString());
    this.requestBody = await this.response.json();
});

Given('RequestParamの {string} に ResponseBodyの {string} を設定', async function (name, path) {
    this.params = this.params || {};
    const value = JSONPath({ json: this.requestBody, path });
    this.params[name] = value[0];
});

Given('RequestParamの {string} に {string} を設定', async function (this: ThisWorld, name, value) {
    this.params = this.params || {};
    this.params[name] = value;
});

Then('ResponseBodyの {string} がnullであること', async function (string) {
    const value = JSONPath({ json: this.requestBody, path: string });
    expect(value[0]).to.be.null;
});