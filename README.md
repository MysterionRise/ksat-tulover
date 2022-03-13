## Different Rust-oriented plays, nothing serious, nothing crazy

### Learning Programming-Rust-Fast-Systems-Development

#### Chapter2

##### gcd

```
cd programming-rust-fast-systems-development/ch2/gcd
cargo test
cargo run 799459 28823 27347
```

##### actix-gcd

```
cd programming-rust-fast-systems-development/ch2/actix-gcd
cargo run
```
Check http://0.0.0.0:3000 to see the results

##### mandelbrot

For normal testing
```
cd programming-rust-fast-systems-development/ch2/mandelbrot
cargo run mandel.png 300 1600x1200 -1.20,0.35 -1,0.20
```
For maximum performance (at least 20 times faster)
```
cd programming-rust-fast-systems-development/ch2/mandelbrot
cargo build --release
./target/release/mandelbrot mandel.png 300 1600x1200 -1.20,0.35 -1,0.20
```


Books:

- https://doc.rust-lang.org/book/
- https://www.amazon.com/Programming-Rust-Fast-Systems-Development/dp/1492052590/ref=sr_1_2?crid=33W305STFTGYI&keywords=rust&qid=1645462833&sprefix=rust+%2Caps%2C307&sr=8-2 good to start from
- https://www.amazon.com/Rust-Rustaceans-Programming-Experienced-Developers/dp/1718501854/ref=sr_1_1?keywords=rust+for+rustaceans+book&qid=1645462920&sprefix=rust+for+rus%2Caps%2C123&sr=8-1 later after 6 months of Rust
- https://www.amazon.com/dp/1680508164/?_encoding=UTF8&pd_rd_w=wWIHX&pf_rd_p=9aa30bae-d685-4626-879d-c38f81e830a3&pf_rd_r=5C5ZSPPT91JDVJ8SRD2V&pd_rd_r=7d83d1f5-b1d8-49ff-adb1-b61baa036490&pd_rd_wg=ZOAfs&ref_=bd_tags_dp_rec - game dev fun in Rust
- https://rust-unofficial.github.io/too-many-lists/ - good ebook
