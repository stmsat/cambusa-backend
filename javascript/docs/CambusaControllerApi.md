# CambusaController.CambusaControllerApi

All URIs are relative to *http://127.0.0.1:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deletePosizione**](CambusaControllerApi.md#deletePosizione) | **DELETE** /cambusa/posizioni/{id} | 
[**deleteProdotto**](CambusaControllerApi.md#deleteProdotto) | **DELETE** /cambusa/prodotti/{id} | 
[**deleteTipo**](CambusaControllerApi.md#deleteTipo) | **DELETE** /cambusa/tipi/{id} | 
[**getPosizione**](CambusaControllerApi.md#getPosizione) | **GET** /cambusa/posizioni/{id} | 
[**getPosizioni**](CambusaControllerApi.md#getPosizioni) | **GET** /cambusa/posizioni | 
[**getProdotti**](CambusaControllerApi.md#getProdotti) | **GET** /cambusa/prodotti | 
[**getProdotto**](CambusaControllerApi.md#getProdotto) | **GET** /cambusa/prodotti/{id} | 
[**getTipi**](CambusaControllerApi.md#getTipi) | **GET** /cambusa/tipi | 
[**getTipo**](CambusaControllerApi.md#getTipo) | **GET** /cambusa/tipi/{id} | 
[**patchProdotto**](CambusaControllerApi.md#patchProdotto) | **PATCH** /cambusa/prodotti/{id} | 
[**putPosizione**](CambusaControllerApi.md#putPosizione) | **PUT** /cambusa/posizioni | 
[**putProdotto**](CambusaControllerApi.md#putProdotto) | **PUT** /cambusa/prodotti | 
[**putTipo**](CambusaControllerApi.md#putTipo) | **PUT** /cambusa/tipi | 

<a name="deletePosizione"></a>
# **deletePosizione**
> &#x27;String&#x27; deletePosizione(id)



Elimina una Posizione

### Example
```javascript
import {CambusaController} from 'cambusa_controller';

let apiInstance = new CambusaController.CambusaControllerApi();
let id = "38400000-8cf0-11bd-b23e-10b96e4ef00d"; // String | 

apiInstance.deletePosizione(id, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | [**String**](.md)|  | 

### Return type

**&#x27;String&#x27;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain

<a name="deleteProdotto"></a>
# **deleteProdotto**
> &#x27;String&#x27; deleteProdotto(id)



Elimina un Prodotto

### Example
```javascript
import {CambusaController} from 'cambusa_controller';

let apiInstance = new CambusaController.CambusaControllerApi();
let id = "38400000-8cf0-11bd-b23e-10b96e4ef00d"; // String | 

apiInstance.deleteProdotto(id, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | [**String**](.md)|  | 

### Return type

**&#x27;String&#x27;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain

<a name="deleteTipo"></a>
# **deleteTipo**
> &#x27;String&#x27; deleteTipo(id)



Elimina un Tipo

### Example
```javascript
import {CambusaController} from 'cambusa_controller';

let apiInstance = new CambusaController.CambusaControllerApi();
let id = "38400000-8cf0-11bd-b23e-10b96e4ef00d"; // String | 

apiInstance.deleteTipo(id, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | [**String**](.md)|  | 

### Return type

**&#x27;String&#x27;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain

<a name="getPosizione"></a>
# **getPosizione**
> Posizione getPosizione(id)



Posizione per Id

### Example
```javascript
import {CambusaController} from 'cambusa_controller';

let apiInstance = new CambusaController.CambusaControllerApi();
let id = "38400000-8cf0-11bd-b23e-10b96e4ef00d"; // String | 

apiInstance.getPosizione(id, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | [**String**](.md)|  | 

### Return type

[**Posizione**](Posizione.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPosizioni"></a>
# **getPosizioni**
> [Posizione] getPosizioni(opts)



Lista delle posizioni, ricercabile per nome e ordinabile

### Example
```javascript
import {CambusaController} from 'cambusa_controller';

let apiInstance = new CambusaController.CambusaControllerApi();
let opts = { 
  'name': "name_example", // String | 
  'sortby': ["[\"name\"]"], // [String] | 
  'sortdirection': ["[\"ASC\"]"] // [String] | 
};
apiInstance.getPosizioni(opts, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **name** | **String**|  | [optional] 
 **sortby** | [**[String]**](String.md)|  | [optional] [default to [&quot;name&quot;]]
 **sortdirection** | [**[String]**](String.md)|  | [optional] [default to [&quot;ASC&quot;]]

### Return type

[**[Posizione]**](Posizione.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProdotti"></a>
# **getProdotti**
> [Prodotto] getProdotti(opts)



Lista dei prodotti, ricercabile per vari parametri

### Example
```javascript
import {CambusaController} from 'cambusa_controller';

let apiInstance = new CambusaController.CambusaControllerApi();
let opts = { 
  'name': "", // String | 
  'dataScadenzaLt': new Date("2013-10-20"), // Date | 
  'dataScadenzaGenerataLt': new Date("2013-10-20"), // Date | 
  'dataScadenzaGt': new Date("2013-10-20"), // Date | 
  'dataScadenzaGenerataGt': new Date("2013-10-20"), // Date | 
  'tipo': [new CambusaController.Tipo()], // [Tipo] | 
  'posizione': [new CambusaController.Posizione()], // [Posizione] | 
  'sortby': ["[\"name\"]"], // [String] | 
  'sortdirection': ["[\"ASC\"]"] // [String] | 
};
apiInstance.getProdotti(opts, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **name** | **String**|  | [optional] 
 **dataScadenzaLt** | **Date**|  | [optional] 
 **dataScadenzaGenerataLt** | **Date**|  | [optional] 
 **dataScadenzaGt** | **Date**|  | [optional] 
 **dataScadenzaGenerataGt** | **Date**|  | [optional] 
 **tipo** | [**[Tipo]**](Tipo.md)|  | [optional] 
 **posizione** | [**[Posizione]**](Posizione.md)|  | [optional] 
 **sortby** | [**[String]**](String.md)|  | [optional] [default to [&quot;name&quot;]]
 **sortdirection** | [**[String]**](String.md)|  | [optional] [default to [&quot;ASC&quot;]]

### Return type

[**[Prodotto]**](Prodotto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProdotto"></a>
# **getProdotto**
> Prodotto getProdotto(id)



Prodotto per Id

### Example
```javascript
import {CambusaController} from 'cambusa_controller';

let apiInstance = new CambusaController.CambusaControllerApi();
let id = "38400000-8cf0-11bd-b23e-10b96e4ef00d"; // String | 

apiInstance.getProdotto(id, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | [**String**](.md)|  | 

### Return type

[**Prodotto**](Prodotto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getTipi"></a>
# **getTipi**
> [Tipo] getTipi(opts)



Lista dei tipi, ricercabile per nome e ordinabile

### Example
```javascript
import {CambusaController} from 'cambusa_controller';

let apiInstance = new CambusaController.CambusaControllerApi();
let opts = { 
  'name': "name_example", // String | 
  'sortby': ["[\"name\"]"], // [String] | 
  'sortdirection': ["[\"ASC\"]"] // [String] | 
};
apiInstance.getTipi(opts, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **name** | **String**|  | [optional] 
 **sortby** | [**[String]**](String.md)|  | [optional] [default to [&quot;name&quot;]]
 **sortdirection** | [**[String]**](String.md)|  | [optional] [default to [&quot;ASC&quot;]]

### Return type

[**[Tipo]**](Tipo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getTipo"></a>
# **getTipo**
> Tipo getTipo(id)



Tipo per Id

### Example
```javascript
import {CambusaController} from 'cambusa_controller';

let apiInstance = new CambusaController.CambusaControllerApi();
let id = "38400000-8cf0-11bd-b23e-10b96e4ef00d"; // String | 

apiInstance.getTipo(id, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | [**String**](.md)|  | 

### Return type

[**Tipo**](Tipo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="patchProdotto"></a>
# **patchProdotto**
> Prodotto patchProdotto(id, opts)



Aggiorna singoli campi di un Prodotto

### Example
```javascript
import {CambusaController} from 'cambusa_controller';

let apiInstance = new CambusaController.CambusaControllerApi();
let id = "38400000-8cf0-11bd-b23e-10b96e4ef00d"; // String | 
let opts = { 
  'aperto': true, // Boolean | 
  'dataApertura': new Date("2013-10-20"), // Date | 
  'quantita': 56 // Number | 
};
apiInstance.patchProdotto(id, opts, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | [**String**](.md)|  | 
 **aperto** | **Boolean**|  | [optional] 
 **dataApertura** | **Date**|  | [optional] 
 **quantita** | **Number**|  | [optional] 

### Return type

[**Prodotto**](Prodotto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="putPosizione"></a>
# **putPosizione**
> Posizione putPosizione(body)



Salva una Posizione (insert o update)

### Example
```javascript
import {CambusaController} from 'cambusa_controller';

let apiInstance = new CambusaController.CambusaControllerApi();
let body = new CambusaController.Posizione(); // Posizione | 

apiInstance.putPosizione(body, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Posizione**](Posizione.md)|  | 

### Return type

[**Posizione**](Posizione.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="putProdotto"></a>
# **putProdotto**
> Prodotto putProdotto(body)



Salva un Prodotto (insert o update)

### Example
```javascript
import {CambusaController} from 'cambusa_controller';

let apiInstance = new CambusaController.CambusaControllerApi();
let body = new CambusaController.Prodotto(); // Prodotto | 

apiInstance.putProdotto(body, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Prodotto**](Prodotto.md)|  | 

### Return type

[**Prodotto**](Prodotto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="putTipo"></a>
# **putTipo**
> Tipo putTipo(body)



Salva un Tipo (insert o update)

### Example
```javascript
import {CambusaController} from 'cambusa_controller';

let apiInstance = new CambusaController.CambusaControllerApi();
let body = new CambusaController.Tipo(); // Tipo | 

apiInstance.putTipo(body, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Tipo**](Tipo.md)|  | 

### Return type

[**Tipo**](Tipo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

