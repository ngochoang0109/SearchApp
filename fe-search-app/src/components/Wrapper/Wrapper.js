const Wrapper = (props) => {
    return (
        <div className="wrapper d-flex align-items-stretch">
            {props.children}
        </div>
    )
}

export default Wrapper;