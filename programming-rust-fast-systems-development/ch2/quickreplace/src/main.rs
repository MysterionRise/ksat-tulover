use std::env;
use std::fs;
use text_colorizer::*;
use regex::Regex;

#[derive(Debug)]
struct Arguments {
    target: String,
    replacement: String,
    filename: String,
    output: String,
}

fn replace(target: &str, replacement: &str, text: &str) -> Result<String, regex::Error> {
    let regex = Regex::new(target)?;
    Ok(regex.replace_all(text, replacement).to_string())
}

fn parse_arguments() -> Arguments {
    let args: Vec<String> = env::args().skip(1).collect();

    if args.len() != 4 {
        print_usage();
        eprintln!("{} wrong number of arguments: expected 4, got {}", "Error:".red().bold(), args.len().to_string().red());
    }

    Arguments {
        target: args[0].clone(),
        replacement: args[1].clone(),
        filename: args[2].clone(),
        output: args[3].clone(),
    }
}

fn print_usage() {
    eprintln!("{} - change occurrences of one string to another", "quickreplace".green());
    eprintln!("Usage: quickreplace <target> <replacement> <INPUT> <OUTPUT>");
}


fn main() {
    let error: ColoredString = "Error:".red().bold();
    let args = parse_arguments();

    let data = match fs::read_to_string(&args.filename) {
        Ok(v) => v,
        Err(e) => {
            eprintln!("{} failed to read from file '{}': {:?}", error, args.filename.red(), e);
            std::process::exit(1);
        }
    };

    let replaced_data = match replace(&args.target, &args.replacement, &data) {
        Ok(v) => v,
        Err(e) => {
            eprintln!("{} failed to replace text {:?}", error, e);
            std::process::exit(1);
        }
    };

    match fs::write(&args.output, &replaced_data) {
        Ok(_) => {}
        Err(e) => {
            eprintln!("{} failed to write to file '{}': {:?}", error, args.output.red(), e);
            std::process::exit(1);
        }
    }
}
