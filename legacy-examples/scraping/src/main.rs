use std::collections::HashMap;

#[tokio::main]
async fn main() -> Result<(), Box<dyn std::error::Error>> {

    let res = reqwest::get("https://www.hltv.org/results?event=4443&matchType=Lan").await?;

    println!("Status: {}", res.status());

    let body = res.text().await?;

    println!("Body:\n\n{}", body);

    Ok(())

}