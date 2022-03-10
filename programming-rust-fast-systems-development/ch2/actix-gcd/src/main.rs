use actix_web::{App, HttpRequest, HttpResponse, HttpServer, middleware, Responder, Result, web};
use serde::{Deserialize, Serialize};

struct AppState {
    secret: String,
}

fn gcd(mut n: u64, mut m: u64) -> u64 {
    assert!(n != 0 && m != 0);
    while m != 0 {
        if m < n {
            let t = m;
            m = n;
            n = t;
        }
        m = m % n;
    }
    n
}

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    let server = HttpServer::new(|| {
        App::new()
            .wrap(middleware::Logger::default())
            .configure(app_config)
    });

    println!("Working on http://0.0.0.0:3000");

    server.bind("0.0.0.0:3000")?.run().await
}

fn app_config(config: &mut web::ServiceConfig) {
    config.service(
        web::scope("")
            .app_data(web::Data::new(AppState {
                secret: "secret_phrase".to_string(),
            }))
            .service(web::resource("/").route(web::get().to(index_page)))
            .service(web::resource("/gcd").route(web::post().to(handle_gcd_post)))
    );
}

async fn index_page() -> Result<HttpResponse> {
    Ok(HttpResponse::Ok()
        .content_type("text/html; charset=utf-8")
        .body(
            r#"
                <title>GCD calculation in Rust</title>
                <form action="/gcd" method="post">
                    <input type="text" name="n"/>
                    <input type="text" name="m"/>
                    <button type="submit">Compute GCD</button>
                </form>
            "#,
        )
    )
}

#[derive(Serialize, Deserialize)]
pub struct GCDParams {
    n: u64,
    m: u64,
}

/// Handle GCD params
async fn handle_gcd_post(params: web::Form<GCDParams>) -> Result<HttpResponse> {
    if params.n == 0 || params.m == 0 {
        return Ok(HttpResponse::BadRequest()
            .content_type("text/plain").body("GCD with 0 doesn't make much sense"));
    }

    Ok(HttpResponse::Ok()
        .content_type("text/plain")
        .body(format!("GCD of {} and {} is {}", params.n, params.m, gcd(params.n, params.m))))
}
