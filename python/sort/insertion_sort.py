def insertion_sort(input_list):
    for i in range(1, len(input_list)):
        j = i
        while (j > 0 and input_list[j-1] > input_list[j]):
            input_list[j-1], input_list[j] = input_list[j], input_list[j-1]
            j -= 1
    return input_list