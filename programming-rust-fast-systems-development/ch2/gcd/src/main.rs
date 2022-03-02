use std::str::FromStr;
use std::env;

fn main() {
    let mut args = Vec::new();

    for arg in env::args().skip(1) {
        args.push(u64::from_str(&arg).expect("Not a number"));
    }

    if args.len() == 0 {
        println!("No arguments provided, usage gcd NUMBER ... ...");
        return;
    }

    let mut d = args[1];
    for m in &args[1..] {
        d = gcd(d, *m);
    }

    println!("The gcd of {:?} is {}", args, d);
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


#[test]
fn test_gcd() {
    assert_eq!(gcd(14, 15), 1);

    assert_eq!(gcd(2 * 3 * 5 * 11 * 17,
                   3 * 7 * 11 * 13 * 19),
               3 * 11);
}